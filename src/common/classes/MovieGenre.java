package common.classes;

import java.io.Serializable;

/**
 * Набор констант, описывающих возможные жанры фильма.
 */
public enum MovieGenre implements Serializable {
    WESTERN,
    TRAGEDY,
    THRILLER,
    HORROR;
    private final static long serialVersionUID = 7L;
}
