package common.classes;

import java.io.Serializable;

/**
 * Набор констант, описывающих возможные возрастные ограничения фильма.
 */
public enum MpaaRating implements Serializable {
    G,
    PG,
    PG_13,
    R,
    NC_17;
    private final static long serialVersionUID = 8L;
}
