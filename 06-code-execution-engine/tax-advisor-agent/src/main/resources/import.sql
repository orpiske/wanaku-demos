-- Default tax rates for SALARY
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (1, 'SALARY', 'CZ', 22.50, 'Czech Republic salary tax rate');
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (2, 'SALARY', 'BR', 27.50, 'Brazil salary tax rate');
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (3, 'SALARY', 'DEFAULT', 25.00, 'Default salary tax rate for other countries');

-- Default tax rates for STOCK_RECEIVED
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (4, 'STOCK_RECEIVED', 'CZ', 22.50, 'Czech Republic stock received tax rate');
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (5, 'STOCK_RECEIVED', 'BR', 27.50, 'Brazil stock received tax rate');
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (6, 'STOCK_RECEIVED', 'DEFAULT', 25.00, 'Default stock received tax rate for other countries');

-- Default tax rates for STOCK_SOLD
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (7, 'STOCK_SOLD', 'CZ', 22.50, 'Czech Republic stock sold tax rate');
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (8, 'STOCK_SOLD', 'BR', 27.50, 'Brazil stock sold tax rate');
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (9, 'STOCK_SOLD', 'DEFAULT', 25.00, 'Default stock sold tax rate for other countries');

-- Default tax rates for OTHER_INCOME
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (10, 'OTHER_INCOME', 'CZ', 35.00, 'Czech Republic other income tax rate');
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (11, 'OTHER_INCOME', 'BR', 35.00, 'Brazil other income tax rate');
INSERT INTO tax_rate_config (id, category, country_code, rate, description) VALUES (12, 'OTHER_INCOME', 'DEFAULT', 35.00, 'Default other income tax rate for other countries');

-- Update sequence for H2
ALTER TABLE tax_rate_config ALTER COLUMN id RESTART WITH 13;
