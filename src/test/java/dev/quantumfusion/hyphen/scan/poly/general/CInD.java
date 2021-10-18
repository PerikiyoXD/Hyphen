package dev.quantumfusion.hyphen.scan.poly.general;

import dev.quantumfusion.hyphen.scan.annotations.Data;
import dev.quantumfusion.hyphen.scan.annotations.DataSubclasses;
import dev.quantumfusion.hyphen.scan.poly.classes.C1;
import dev.quantumfusion.hyphen.scan.poly.classes.C2;
import dev.quantumfusion.hyphen.scan.poly.classes.D1;
import dev.quantumfusion.hyphen.scan.poly.classes.D2;
import dev.quantumfusion.hyphen.util.TestThis;

@Data
@TestThis
public class CInD {
	@DataSubclasses({D1.class, D2.class})
	public D1<@DataSubclasses({C1.class, C2.class}) C1<Integer>> data;

	public CInD(D1<C1<Integer>> data) {
		this.data = data;
	}
}
