public class NoteID {

	/**
	 *Determine whether correct note is played
	 *@param f The target frequency(Hz)
	 *@param b The byte array to be analyzed
	 *@param s The sample rate of the byte array
	 */
	public String noteID (byte[] b) {
		int[] diff = new int[12];
		byte[][] a = new byte[12][];
		
		String[] n = new String[12];
		n[0] = "g#";
		n[1] = "g";
		n[2] = "f#";
		n[3] = "f";
		n[4] = "e";
		n[5] = "d#";
		n[6] = "d";
		n[7] = "c#";
		n[8] = "c";
		n[9] = "b";
		n[10] = "a#";
		n[11] = "a";

		a[0] = new byte[212];//gSharp3
		a[1] = new byte[225];//g3
		a[2] = new byte[238];//fSharp3
		a[3] = new byte[253];//f3
		a[4] = new byte[268];//e3
		a[5] = new byte[283];//dSharp3
		a[6] = new byte[300];//d3
		a[7] = new byte[318];//cSharp3
		a[8] = new byte[337];//c3
		a[9] = new byte[357];//b2
		a[10] = new byte[378];//aSharp2
		a[11] = new byte[401];//a2


		for (int i = 0; i < diff.length; i++) {
			diff[i] = fillAndMaxMinDiff(a[i], b);
		}
		
		int max = 0;
		int maxIndex = 0;
		for (int i = 0; i < diff.length; i++) {
			if (diff[i] > max) {
				max = diff[i];
				maxIndex = i;
			} 
		}
		return n[maxIndex];
	}
	public int fillAndMaxMinDiff(byte[] a, byte[] b) {
		int max = a[0];
		int min = a[0];
		int x = 0;
		for(int i = 0; i < b.length; i++) {
			if (x >= a.length)
				x = 0;
			a[x] += b[i];
			x++;
		}
		
		for (int i = 0; i < a.length; i++) {
			max = Math.max(a[i], max);
			min = Math.min(a[i], min);
		}
		return max-min;
	}

}