package ru.fastdelivery.domain.delivery.shipment;

import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.dimensions.TotalVolume;
import ru.fastdelivery.domain.delivery.pack.PackOfVolume;

import java.util.List;

public record ShipmentOfVolume(List<PackOfVolume> packages,
                               Currency currency) {
    public TotalVolume volumeAllPackages() {
        return packages.stream()
                .map(PackOfVolume::totalVolume)
                .reduce(TotalVolume::add)
                .get();
    }
}
