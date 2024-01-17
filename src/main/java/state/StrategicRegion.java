package state;

import java.util.List;
import misc.Color;
import province.Province;

record StrategicRegion(
    String graphicalCulture, Province capitalProvince, Color mapColor, List<State> states) {}
