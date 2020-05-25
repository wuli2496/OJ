
class Solution {
	public boolean validUtf8(int[] data) {
		int numOfNeedProcess = 0;
		int mask1 = 1 << 7;
		int mask2 = 1 << 6;
		
		for (int i = 0; i < data.length; ++i) {
			if (numOfNeedProcess == 0) {
				int mask = 1 << 7;
				while ((mask & data[i]) != 0) {
					++numOfNeedProcess;
					mask >>>= 1;
				}
				
				if (numOfNeedProcess == 0) {
					continue;
				}
				
				if (numOfNeedProcess > 4 || numOfNeedProcess == 1) {
					return false;
				}
			} else {
				if ((data[i] & mask1) == 0 || (data[i] & mask2) != 0) {
					return false;
				}
			}
			
			--numOfNeedProcess;
		}
		
		return numOfNeedProcess == 0;
    }
}