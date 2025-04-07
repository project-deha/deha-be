package org.deha.be.dto.email;

import org.deha.be.model.User;
import org.deha.be.model.earthquakes.Earthquake;

public record SendEarthquakeEmailParameters(User user, Earthquake earthquake) {
}
