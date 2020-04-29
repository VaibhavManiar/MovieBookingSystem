package com.bookmyticket.db;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MovieSeatHoldCache {
    Duration duration = Duration.ofMinutes(5);

    // Show vs seat vs timing
    Map<String, Map<String, LocalDateTime>> holdSeatMap = new ConcurrentHashMap<>();

    // Show number vs Seat Number Vs User Id map
    Map<String, Map<String, String>> showVsSeatVsUserMap = new ConcurrentHashMap<>();

    Lock lock = new ReentrantLock();

    public void holdSeat(String showNumber, Set<String> seatNumbers, String userId) {
        try {
            lock.lock();
            LocalDateTime now = LocalDateTime.now();
            Map<String, LocalDateTime> map = holdSeatMap.getOrDefault(showNumber, new ConcurrentHashMap<>());
            Map<String, String> seatVsUserMap = this.showVsSeatVsUserMap.getOrDefault(showNumber, new ConcurrentHashMap<>());
            for (String seatNumber : seatNumbers) {
                LocalDateTime localDateTime = map.getOrDefault(seatNumber, null);
                if (Objects.nonNull(localDateTime) && localDateTime.compareTo(LocalDateTime.now()) >= 0) {
                    throw new RuntimeException("Seat is already holded");
                }
                seatVsUserMap.put(seatNumber, userId);
                this.showVsSeatVsUserMap.put(showNumber, seatVsUserMap);
                holdSeatMap.get(showNumber).put(seatNumber, now);
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean isSeatHoldedForNext30Sec(String showNumber, String seatNumber) {
        LocalDateTime localDateTime = this.holdSeatMap.getOrDefault(showNumber, Collections.emptyMap()).getOrDefault(seatNumber, null);
        return Objects.nonNull(localDateTime) && localDateTime.compareTo(LocalDateTime.now().minusSeconds(30)) > 0;
    }

    public boolean isSeatHoldedForNext30SecByUser(String showNumber, String seatNumber, String userId) {
        return isSeatHoldedForNext30Sec(showNumber, seatNumber) && this.showVsSeatVsUserMap.getOrDefault(showNumber, Collections.emptyMap()).getOrDefault(seatNumber, "").equals(userId);
    }

    public void releaseSeat(String showNumber, String seatNumber) {
        this.showVsSeatVsUserMap.getOrDefault(showNumber, Collections.emptyMap()).remove(seatNumber);
        this.holdSeatMap.getOrDefault(showNumber, Collections.emptyMap()).remove(seatNumber);
    }
}
