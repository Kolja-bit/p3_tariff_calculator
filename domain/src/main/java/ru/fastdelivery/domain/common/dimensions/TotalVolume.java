package ru.fastdelivery.domain.common.dimensions;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

//@RequiredArgsConstructor
public class TotalVolume {
    /*private final CargoVolume cargoVolume;
    public TotalVolume add(TotalVolume totalVolumeOfCargo){
        return new TotalVolume((CargoVolume) this.cargoVolume.getVolumeOfOnePackage()
                .add(totalVolumeOfCargo.cargoVolume.getVolumeOfOnePackage()));
    }
    public BigDecimal squareMeter() {
        return new BigDecimal(String.valueOf(cargoVolume.getVolumeOfOnePackage()));
    }*/
    private final BigDecimal volumeOfCargo;
    public TotalVolume(BigDecimal volumeOfCargo){
        this.volumeOfCargo=volumeOfCargo;
    }
    public TotalVolume add(TotalVolume totalVolumeOfCargo) {
        return new TotalVolume(this.volumeOfCargo
                .add(totalVolumeOfCargo.volumeOfCargo));
    }
    public BigDecimal squareMeter() {
        return new BigDecimal(String.valueOf(volumeOfCargo));
    }
}
