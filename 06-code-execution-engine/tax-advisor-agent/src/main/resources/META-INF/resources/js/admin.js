function adminApp() {
    return {
        rates: [],
        editingId: null,
        newRate: {
            category: '',
            countryCode: '',
            rate: '',
            description: ''
        },
        editRate: {
            category: '',
            countryCode: '',
            rate: '',
            description: ''
        },

        async loadRates() {
            try {
                const response = await fetch('/api/admin/tax-rates');
                this.rates = await response.json();
            } catch (error) {
                console.error('Error loading rates:', error);
                alert('Failed to load tax rates');
            }
        },

        async createRate() {
            try {
                const response = await fetch('/api/admin/tax-rates', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        category: this.newRate.category,
                        countryCode: this.newRate.countryCode.toUpperCase(),
                        rate: parseFloat(this.newRate.rate),
                        description: this.newRate.description
                    })
                });

                if (response.ok) {
                    this.newRate = { category: '', countryCode: '', rate: '', description: '' };
                    await this.loadRates();
                } else {
                    alert('Failed to create tax rate');
                }
            } catch (error) {
                console.error('Error creating rate:', error);
                alert('Failed to create tax rate');
            }
        },

        startEdit(rate) {
            this.editingId = rate.id;
            this.editRate = {
                category: rate.category,
                countryCode: rate.countryCode,
                rate: rate.rate,
                description: rate.description
            };
        },

        cancelEdit() {
            this.editingId = null;
            this.editRate = { category: '', countryCode: '', rate: '', description: '' };
        },

        async saveEdit() {
            try {
                const response = await fetch(`/api/admin/tax-rates/${this.editingId}`, {
                    method: 'PUT',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        category: this.editRate.category,
                        countryCode: this.editRate.countryCode.toUpperCase(),
                        rate: parseFloat(this.editRate.rate),
                        description: this.editRate.description
                    })
                });

                if (response.ok) {
                    this.cancelEdit();
                    await this.loadRates();
                } else {
                    alert('Failed to update tax rate');
                }
            } catch (error) {
                console.error('Error updating rate:', error);
                alert('Failed to update tax rate');
            }
        },

        async deleteRate(id) {
            if (!confirm('Are you sure you want to delete this tax rate?')) return;

            try {
                const response = await fetch(`/api/admin/tax-rates/${id}`, {
                    method: 'DELETE'
                });

                if (response.ok) {
                    await this.loadRates();
                } else {
                    alert('Failed to delete tax rate');
                }
            } catch (error) {
                console.error('Error deleting rate:', error);
                alert('Failed to delete tax rate');
            }
        }
    };
}
