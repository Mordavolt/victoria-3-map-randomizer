package lv.kitn.generator;

import java.util.Optional;

public record Population(Culture culture, Optional<String> religion, Optional<String> popType) {}
