package com.medishare.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum TimeZoneCategory {
    // タイミング
    WAKE_UP("起床時", "タイミング", "wakeUp", "wake-up-photo.webp"),
    BEFORE_BREAKFAST("朝食前", "タイミング", "beforeBreakfast", "before-breakfast-photo.webp"),
    AFTER_BREAKFAST("朝食後", "タイミング", "afterBreakfast", "after-breakfast-photo.webp"),
    BEFORE_LUNCH("昼食前", "タイミング", "beforeLunch", "before-lunch-photo.webp"),
    AFTER_LUNCH("昼食後", "タイミング", "afterLunch", "after-lunch-photo.webp"),
    BEFORE_DINNER("夕食前", "タイミング", "beforeDinner", "before-dinner-photo.webp"),
    AFTER_DINNER("夕食後", "タイミング", "afterDinner", "after-dinner-photo.webp"),
    BEFORE_SLEEP("就寝前", "タイミング", "beforeSleep", "before-sleep-photo.webp"),
    BETWEEN_MEALS("食間", "タイミング", "betweenMeals", "between-meals-photo.webp"),

    // 時間指定
    ZERO_O_CLOCK("0時", "時間指定", "zeroOClock", null),
    ONE_O_CLOCK("1時", "時間指定", "oneOClock", null),
    TWO_O_CLOCK("2時", "時間指定", "twoOClock", null),
    THREE_O_CLOCK("3時", "時間指定", "threeOClock", null),
    FOUR_O_CLOCK("4時", "時間指定", "fourOClock", null),
    FIVE_O_CLOCK("5時", "時間指定", "fiveOClock", null),
    SIX_O_CLOCK("6時", "時間指定", "sixOClock", null),
    SEVEN_O_CLOCK("7時", "時間指定", "sevenOClock", null),
    EIGHT_O_CLOCK("8時", "時間指定", "eightOClock", null),
    NINE_O_CLOCK("9時", "時間指定", "nineOClock", null),
    TEN_O_CLOCK("10時", "時間指定", "tenOClock", null),
    ELEVEN_O_CLOCK("11時", "時間指定", "elevenOClock", null),
    TWELVE_O_CLOCK("12時", "時間指定", "twelveOClock", null),
    THIRTEEN_O_CLOCK("13時", "時間指定", "thirteenOClock", null),
    FOURTEEN_O_CLOCK("14時", "時間指定", "fourteenOClock", null),
    FIFTEEN_O_CLOCK("15時", "時間指定", "fifteenOClock", null),
    SIXTEEN_O_CLOCK("16時", "時間指定", "sixteenOClock", null),
    SEVENTEEN_O_CLOCK("17時", "時間指定", "seventeenOClock", null),
    EIGHTEEN_O_CLOCK("18時", "時間指定", "eighteenOClock", null),
    NINETEEN_O_CLOCK("19時", "時間指定", "nineteenOClock", null),
    TWENTY_O_CLOCK("20時", "時間指定", "twentyOClock", null),
    TWENTY_ONE_O_CLOCK("21時", "時間指定", "twentyOneOClock", null),
    TWENTY_TWO_O_CLOCK("22時", "時間指定", "twentyTwoOClock", null),
    TWENTY_THREE_O_CLOCK("23時", "時間指定", "twentyThreeOClock", null);

    private final String label;
    private final String category;
    private final String attrPrefix;
    private final String photoName;

    TimeZoneCategory(String label, String category, String attrPrefix, String photoName) {
        this.label = label;
        this.category = category;
        this.attrPrefix = attrPrefix;
        this.photoName = photoName;
    }

    public String getLabel() {
        return label;
    }

    public String getCategory() {
        return category;
    }

    public String getAttrPrefix() {
        return attrPrefix;
    }

    public String getPhotoName() {
        return photoName;
    }

    private static final Logger logger = LoggerFactory.getLogger(TimeZoneCategory.class);

    public static TimeZoneCategory fromLabel(String label) {
        for (TimeZoneCategory category : TimeZoneCategory.values()) {
            if (category.getLabel().equals(label)) {
                return category;
            }
        }

        logger.warn("No TimeZoneCategory found for label: {}", label);
        throw new IllegalArgumentException("No TimeZoneCategory found for label: " + label);
    }

    public boolean isTiming() {
        return "タイミング".equals(this.category);
    }

    public boolean isSelectedTime() {
        return "時間指定".equals(this.category);
    }
}
