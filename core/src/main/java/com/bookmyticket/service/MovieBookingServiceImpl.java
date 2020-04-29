package com.bookmyticket.service;

import com.bookmyticket.api.MovieShow;
import com.bookmyticket.api.Seat;
import com.bookmyticket.db.MovieHallAndMovieMapping;
import com.bookmyticket.db.MovieSeatHoldCache;

import java.util.Set;

public class MovieBookingServiceImpl implements MovieBookingService {

    private final MovieSeatHoldCache seatHoldCache;
    private final MovieHallAndMovieMapping movieHallAndMovieMapping;
    private final PaymentService paymentService;

    public MovieBookingServiceImpl(MovieSeatHoldCache seatHoldCache,
                                   MovieHallAndMovieMapping movieHallAndMovieMapping,
                                   PaymentService paymentService) {
        this.seatHoldCache = seatHoldCache;
        this.paymentService = paymentService;
        this.movieHallAndMovieMapping = movieHallAndMovieMapping;
    }

    @Override
    public void holdSeat(MovieShow movieShow, Set<String> seatNumbers, String userId) {
        this.seatHoldCache.holdSeat(movieShow.getShowNumber(), seatNumbers, userId);
    }

    @Override
    public void bookSeat(MovieShow movieShow, Set<String> seatNumbers, String userId) {
        Set<Seat> seats = this.movieHallAndMovieMapping.getSeats(movieShow.getShowNumber());
        for(Seat seat: seats) {
            if(!this.seatHoldCache.isSeatHoldedForNext30SecByUser(movieShow.getShowNumber(), seat.getSeatNumber(), userId)) {
                throw new RuntimeException("Seat +["+ seat.getSeatNumber() +"]is not holded please try again.");
            } else {
                int maxRetryCount = 1;
                boolean isPaymentSuccessFull = makePayment(movieShow, maxRetryCount);
                if(isPaymentSuccessFull) {
                    seat.setAvailable(false);
                } else {
                    releaseSeats(movieShow, seatNumbers);
                    throw new RuntimeException("Payment is failed, can not make a booking.");
                }
            }
        }
    }

    /**
     * Used for releasing seats if payment fails of user logs out.
     * @param movieShow
     * @param seatNumbers
     */
    public void releaseSeats(MovieShow movieShow, Set<String> seatNumbers) {
        seatNumbers.forEach(seatNumber-> this.seatHoldCache.releaseSeat(movieShow.getShowNumber(), seatNumber));
    }

    private boolean makePayment(MovieShow movieShow, int maxRetryCount) {
        boolean isPaymentSuccessFull = false;
        for(int i =0; i<= maxRetryCount; i++) {
            try {
                paymentService.makePayment(movieShow.getPrice());
                isPaymentSuccessFull = true;
                break;
            } catch (Exception e) {
                System.out.println("Payment failed : " + e.getMessage());
            }
        }
        return isPaymentSuccessFull;
    }
}
