package guru.springframework.domain;

public enum Difficulty {
    EASY, MODERATE, KIND_OF_HARD, HARD
    // in db:  (default) EnumType.ORDINAL --> 1-N, EnumType.STRING
}
