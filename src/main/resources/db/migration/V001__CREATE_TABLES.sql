CREATE TABLE customer
(
    id    UUID PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE product
(
    id           UUID PRIMARY KEY,
    title        VARCHAR(100)   NOT NULL,
    brand        VARCHAR(100)   NOT NULL,
    image_url    VARCHAR(255)   NOT NULL,
    price        DECIMAL(11, 2) NOT NULL,
    review_score DECIMAL(2, 1)
);


CREATE TABLE customer_wishlist_product
(
    customer_id UUID NOT NULL,
    product_id  UUID NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customer (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);