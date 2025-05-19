CREATE TABLE accident_article (
    id          serial primary key,
    accident_id int references accident(id),
    article_id  int references article(id)
);