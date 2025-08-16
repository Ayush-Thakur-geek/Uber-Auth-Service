package com.uber.UberAuthService.services;

import com.uber.UberAuthService.helpers.AuthPassengerDetails;
import com.uber.UberAuthService.models.Passenger;
import com.uber.UberAuthService.repositories.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/*
*   This class is responsible for loading the user in the form of UserDetails object for auth.
*/

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PassengerRepository passengerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Passenger> passenger = passengerRepository.findPassengerByEmail(username);

        if (passenger.isPresent()) {
            return new AuthPassengerDetails(passenger.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
