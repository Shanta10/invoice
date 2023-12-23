ALTER TABLE client
    ADD COLUMN email VARCHAR(50);

DROP VIEW invoice_view;

ALTER TABLE invoice
ALTER COLUMN total TYPE DECIMAL(10, 2) USING total::numeric(10,2);

CREATE VIEW invoice_view as
select i.*,c.fullname cliente_fullname
from invoice i JOIN client c
ON i.client_id = c.id;



ALTER TABLE product
ALTER COLUMN price TYPE DECIMAL(10, 2);

ALTER TABLE detail
ALTER COLUMN price TYPE DECIMAL(10, 2);

