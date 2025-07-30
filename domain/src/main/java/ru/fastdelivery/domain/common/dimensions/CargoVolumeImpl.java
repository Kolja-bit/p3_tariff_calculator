package ru.fastdelivery.domain.common.dimensions;

import java.math.BigDecimal;
import java.math.BigInteger;

public record CargoVolumeImpl(BigInteger length,
                              BigInteger width, BigInteger height) {
    private static final BigInteger SIZE_MAX=new BigInteger(String.valueOf(1500));

    public BigDecimal getVolumeOfOnePackage(){
        if (sizeСheck(length) || sizeСheck(width) || sizeСheck(height)){
            throw new IllegalArgumentException("Size is out of range!");
        }
        BigDecimal resultLength=roundingOfPackagingDimensions(length);
        BigDecimal resultWidth=roundingOfPackagingDimensions(width);
        BigDecimal resultHeight=roundingOfPackagingDimensions(height);
        BigDecimal volumeOfOnePackage = calculationOfCargoPackagingVolume(resultLength,
                resultWidth, resultHeight);
        return volumeOfOnePackage;
    }
    public BigDecimal calculationOfCargoPackagingVolume(BigDecimal length,
                                                        BigDecimal width,BigDecimal height){
        BigDecimal volume = length.multiply(width).multiply(height)
                .divide(new BigDecimal(1000000000));
        return volume;
    }
    public BigDecimal roundingOfPackagingDimensions(BigInteger sizeParameter){
        BigDecimal resultParameter=new BigDecimal(sizeParameter)
                .add(new BigDecimal(50))
                .add(new BigDecimal(sizeParameter)
                        .remainder(new BigDecimal(50)).multiply(new BigDecimal(-1)));
        return resultParameter;
    }
    public boolean sizeСheck(BigInteger size){
        boolean control=false;
        if ( BigInteger.ZERO.compareTo(size)<=0 || SIZE_MAX.compareTo(size)<=0){
            control = true;
        }
        return control;
    }
}
