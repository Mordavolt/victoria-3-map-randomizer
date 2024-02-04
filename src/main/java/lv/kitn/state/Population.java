package lv.kitn.state;

import java.util.Optional;
import lv.kitn.culture.Culture;

public record Population(
    Culture culture, Integer size, Optional<String> religion, Optional<String> popType) {}
