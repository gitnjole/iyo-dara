package iyo.dara.application.overflow;

import java.util.Map;

public record OverflowSplit(
        double overflow,
        double savings,
        double investment,
        Map<String, Double> percentages
) {}
