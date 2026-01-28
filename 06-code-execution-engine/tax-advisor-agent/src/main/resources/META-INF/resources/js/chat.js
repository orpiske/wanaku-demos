function chatApp() {
    return {
        messages: [],
        userInput: '',
        isLoading: false,
        streamingContent: '',

        async sendMessage() {
            const message = this.userInput.trim();
            if (!message) return;

            this.messages.push({ role: 'user', content: message });
            this.userInput = '';
            this.isLoading = true;
            this.streamingContent = '';

            this.scrollToBottom();

            try {
                const response = await fetch('/api/chat', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Accept': 'text/event-stream'
                    },
                    body: JSON.stringify({ message: message })
                });

                const reader = response.body.getReader();
                const decoder = new TextDecoder();
                let fullContent = '';

                while (true) {
                    const { done, value } = await reader.read();
                    if (done) break;

                    const chunk = decoder.decode(value, { stream: true });
                    const lines = chunk.split('\n');

                    for (const line of lines) {
                        if (line.startsWith('data:')) {
                            // Don't trim - preserve spaces in tokens
                            const data = line.substring(5);
                            // Only skip completely empty data (not whitespace-only)
                            if (data !== '') {
                                fullContent += data;
                                this.streamingContent = fullContent;
                                this.scrollToBottom();
                            }
                        }
                    }
                }

                this.messages.push({ role: 'assistant', content: fullContent });
            } catch (error) {
                console.error('Error:', error);
                this.messages.push({
                    role: 'assistant',
                    content: 'Sorry, there was an error processing your request. Please try again.'
                });
            } finally {
                this.isLoading = false;
                this.streamingContent = '';
                this.scrollToBottom();
            }
        },

        askQuestion(question) {
            this.userInput = question;
            this.sendMessage();
        },

        scrollToBottom() {
            this.$nextTick(() => {
                const container = document.getElementById('chatContainer');
                if (container) {
                    container.scrollTop = container.scrollHeight;
                }
            });
        }
    };
}
