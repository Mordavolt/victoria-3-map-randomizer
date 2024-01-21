package lv.kitn.state;

import java.util.List;
import lv.kitn.misc.Color;
import lv.kitn.province.Province;

record StrategicRegion(
    String graphicalCulture, Province capitalProvince, Color mapColor, List<State> states) {}
