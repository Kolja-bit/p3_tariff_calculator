package ru.fastdelivery.domain.common.dimensions;

import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class TotalVolumeOfCargo {//класс не правильный он создает лист а не считает общий объем
    private final CargoVolume cargoVolume;
    private static List<BigInteger> totalVolumeOfCargoList=new ArrayList<>();

    public TotalVolumeOfCargo(CargoVolume cargoVolume) {
        this.cargoVolume = cargoVolume;
    }
    public void addVolume() {
        totalVolumeOfCargoList.add(cargoVolume.getVolumeOfOnePackage());
    }
}
