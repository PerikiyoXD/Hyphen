package net.oskarstrom.hyphen.scan.poly;

import net.oskarstrom.hyphen.TestUtil;
import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.Stream;

public class PolyFactory {

	@TestFactory
	public Stream<DynamicNode> polyTest() {
		return TestUtil.test(SingleStepTest.class);
	}
}
