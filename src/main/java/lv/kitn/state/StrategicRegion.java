package lv.kitn.state;

import java.util.List;

record StrategicRegion(
    String graphicalCulture, Province capitalProvince, Color mapColor, List<State> states) {}
