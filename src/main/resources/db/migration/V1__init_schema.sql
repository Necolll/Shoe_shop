CREATE TABLE products (
    id          BIGSERIAL      PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    brand       VARCHAR(255)   NOT NULL,
    category    VARCHAR(255)   NOT NULL,
    description VARCHAR(2000),
    price       NUMERIC(12, 2) NOT NULL,
    sizes       VARCHAR(255)   NOT NULL,
    image_url   VARCHAR(255)
);

CREATE TABLE orders (
    id            BIGSERIAL      PRIMARY KEY,
    customer_name VARCHAR(255),
    email         VARCHAR(255),
    phone         VARCHAR(255),
    address       VARCHAR(255),
    total         NUMERIC(12, 2),
    created_at    TIMESTAMP(6)
);

CREATE TABLE order_items (
    id           BIGSERIAL      PRIMARY KEY,
    order_id     BIGINT         NOT NULL REFERENCES orders (id),
    product_id   BIGINT,
    product_name VARCHAR(255),
    size         VARCHAR(255),
    quantity     INTEGER        NOT NULL,
    price        NUMERIC(12, 2)
);

CREATE INDEX idx_products_category ON products (category);
CREATE INDEX idx_order_items_order ON order_items (order_id);
