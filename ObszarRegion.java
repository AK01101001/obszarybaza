package com.example.bazaregionow;

import androidx.room.Embedded;

public class ObszarRegion {
    @Embedded
    Obszar obszar;
    @Embedded
    Region region;

}
