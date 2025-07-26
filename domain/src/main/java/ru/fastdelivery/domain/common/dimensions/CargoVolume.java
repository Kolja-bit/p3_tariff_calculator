package ru.fastdelivery.domain.common.dimensions;

import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigDecimal;
import java.math.BigInteger;

public class CargoVolume {
    private static final BigInteger SIZE_MAX=new BigInteger(String.valueOf(1500));
    private final BigInteger length;
    private final BigInteger width;
    private final BigInteger height;
    public CargoVolume(BigInteger length,BigInteger width,BigInteger height){
        this.length=length;
        this.width=width;
        this.height=height;
    }

    public BigInteger getVolumeOfOnePackage(){
        if (sizeСheck(length) || sizeСheck(width) || sizeСheck(height)){
            throw new IllegalArgumentException("Size is out of range!");
        }
        BigDecimal resultLength=roundingOfPackagingDimensions(length);
        BigDecimal resultWidth=roundingOfPackagingDimensions(width);
        BigDecimal resultHeight=roundingOfPackagingDimensions(height);
        BigInteger volumeOfOnePackage = calculationOfCargoPackagingVolume(resultLength,
                resultWidth, resultHeight).toBigInteger();
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
