package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.domain.delivery.shipment.ShipmentOfVolume;

import javax.inject.Named;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;

    public Price calc(Shipment shipment) {
        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
        var minimalPrice = weightPriceProvider.minimalPrice();

        return weightPriceProvider
                .costPerKg()
                .multiply(weightAllPackagesKg)
                .max(minimalPrice);
    }
    public Price calcVolume(ShipmentOfVolume shipmentOfVolume) {
        var volumeAllPackagesSquareMeter = shipmentOfVolume.volumeAllPackages().squareMeter();
        var minimalPrice = weightPriceProvider.minimalPrice();

        return weightPriceProvider
                .costPerSquareMeter()
                .multiply(volumeAllPackagesSquareMeter)
                .max(minimalPrice);
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }
}
