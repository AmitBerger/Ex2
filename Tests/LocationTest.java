import api.DirectedWeightedGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    Location l1;
    Location l2;
    Location l3;

    @BeforeEach
    void initial(){
        l1 = new Location(1,2,3);
        l2 = new Location(4,5,6);
        l3 = new Location(7,8,9);
    }

    @Test
    void x() {
        assertEquals(1,l1.x());
        assertEquals(4,l2.x());
        assertEquals(7,l3.x());
    }

    @Test
    void y() {
        assertEquals(2,l1.y());
        assertEquals(5,l2.y());
        assertEquals(8,l3.y());
    }

    @Test
    void z() {
        assertEquals(3,l1.z());
        assertEquals(6,l2.z());
        assertEquals(9,l3.z());
    }

    @Test
    void distance() {
        assertEquals(4.242640687119285,l1.distance(l2),0.001);
        assertEquals(4.242640687119285,l2.distance(l3),0.001);
        assertEquals(8.48528137423857,l3.distance(l1),0.001);
    }
}