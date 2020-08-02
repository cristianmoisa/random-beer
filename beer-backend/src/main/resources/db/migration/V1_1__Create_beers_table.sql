CREATE SEQUENCE IF NOT EXISTS beers_seq START WITH 1;

CREATE TABLE IF NOT EXISTS beers
(
    id                 integer PRIMARY KEY DEFAULT nextval('beers_seq' :: REGCLASS) not null,
    alcohol_percentage numeric not null,
    brewery_location   text    not null,
    description        text    not null,
    beer_name          text    not null,
    beer_image_url     text    not null
);

INSERT INTO beers (alcohol_percentage, brewery_location, description, beer_name, beer_image_url)
VALUES (5.0, 'U.S.A', 'The best american beer ever', 'Budweiser', 'https://ichef.bbci.co.uk/news/1024/cpsprodpb/61BE/production/_111222052_beerglasses.jpg');
INSERT INTO beers (alcohol_percentage, brewery_location, description, beer_name, beer_image_url)
VALUES (5.9, 'U.K', 'British beer', 'Ale', 'https://trulyexperiences.com/blog/wp-content/uploads/2014/11/featured_0002_Five-Unique-Beers-You-Must-Try.jpg');
INSERT INTO beers (alcohol_percentage, brewery_location, description, beer_name, beer_image_url)
VALUES (2.5, 'Germany', 'Like lemonade', 'Raddler', 'https://ichef.bbci.co.uk/news/1024/cpsprodpb/61BE/production/_111222052_beerglasses.jpg');
INSERT INTO beers (alcohol_percentage, brewery_location, description, beer_name, beer_image_url)
VALUES (7.0, 'Ireland', 'Overpriced irish beer', 'Guinness', 'https://i0.wp.com/post.healthline.com/wp-content/uploads/2020/02/beer-bar-1296x728-header.jpg?w=1155&h=1528');
INSERT INTO beers (alcohol_percentage, brewery_location, description, beer_name, beer_image_url)
VALUES (5.0, 'E.U', 'Very popular beer', 'Heineken', 'https://www.connshg.com/Resources/b5f10bc2-4cd8-4ccf-be25-d8b538cf524c/bigstock-Beer-Cold-Craft-light-Beer-in-202781995.jpg');