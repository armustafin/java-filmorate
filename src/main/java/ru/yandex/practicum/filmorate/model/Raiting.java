package ru.yandex.practicum.filmorate.model;


public enum Raiting {
        G("G — у фильма нет возрастных ограничений"),
        PG("PG — детям рекомендуется смотреть фильм с родителями"),
        PG_13("PG-13 — детям до 13 лет просмотр не желателен"),
        R("R — лицам до 17 лет просматривать фильм можно только в присутствии взрослого"),
        NC_17("NC-17 — лицам до 18 лет просмотр запрещён");
    private final String title;

    Raiting(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "Raiting{" +
                "raiting='" + title + '\'' +
                '}';
    }
}
