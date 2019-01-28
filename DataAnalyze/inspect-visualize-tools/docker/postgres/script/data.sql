CREATE TABLE access_logs(
    id BIGINT NOT NULL,
    access_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    browser VARCHAR(10) NOT NULL,
    iso_country_code VARCHAR(5) NOT NULL,
    access_page VARCHAR(30) NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO
    access_logs(
        id,
        access_at,
        browser,
        iso_country_code,
        access_page
    )
SELECT
    id,
    (
        timestamp '2015-05-17 00:00:00' +
            (
                random_value * 7200 * interval '1 minute'
            )
    ) AS access_at,
    CASE WHEN percent <= 25 THEN 'Chrome'
         WHEN percent <= 47 THEN 'Firefox'
         WHEN percent <= 67 THEN 'IE'
         WHEN percent <= 80 THEN 'Edge'
         WHEN percent <= 90 THEN 'Safari'
         WHEN percent <= 96 THEN 'Opera'
         ELSE 'Other'
    END AS browser,
    CASE WHEN percent <= 40 THEN 'US'
         WHEN percent <= 60 THEN 'CN'
         WHEN percent <= 69 THEN 'FR'
         WHEN percent <= 77 THEN 'DE'
         WHEN percent <= 83 THEN 'IN'
         WHEN percent <= 90 THEN 'SE'
         WHEN percent <= 93 THEN 'JP'
         ELSE 'Other'
    END AS iso_country_code,
    CASE WHEN percent <= 20 THEN 'index.html'
         WHEN percent <= 38 THEN 'main.html'
         WHEN percent <= 54 THEN 'sub-contents.html'
         WHEN percent <= 68 THEN 'bbs.html'
         WHEN percent <= 80 THEN 'profile.html'
         WHEN percent <= 90 THEN 'about.html'
         WHEN percent <= 96 THEN 'link.html'
         ELSE 'Other'
    END AS access_page

FROM
    (
        SELECT
            id,
            random_value,
            (random_value * 100)::integer AS percent
        FROM
            (
                SELECT
                    genereted AS id,
                    random() AS random_value
                FROM
                    generate_series(1, 1000) AS func(genereted)
            ) AS seed
    ) AS base