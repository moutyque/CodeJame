import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
	static boolean hasEliminationOccured = false;
	public static void main(String[] args) {
		Scanner scann = new Scanner(System.in);  // Create a Scanner object
		int T = scann.nextInt();
		

		for(int usease = 0; usease< T;usease++) {
			long score = 0;
			int R = scann.nextInt(); // Row
			int C = scann.nextInt();//Column
			List<Dancer> dancers = new ArrayList<>();
			Dancer[][] S = new Dancer[R][C];
			for(int i = 0; i< R;i++) {
				for(int j = 0;j<C;j++) {
					Dancer d = new Dancer(i, j, scann.nextInt());
					dancers.add(d);
					S[i][j] = d;
					if(j>0) {
						d.westDancer = S[i][j-1];
						S[i][j-1].eastDancer = d;
					}
					if(i>0) {//Not on the first line
						d.northDancer = S[i-1][j];
						S[i-1][j].southDancer = d;
					}
				}				
			}
			hasEliminationOccured = danceContinue(dancers);
			
			do {//danceContinue(dancers)
				score+= turnScore(dancers);
				eliminateDancers(dancers);
				updateDancers(dancers);
				
			}while(hasEliminationOccured);
			//score+= turnScore(dancers);


			System.out.println(String.format("Case #%s: %s",usease+1,score));
		}
	}

	private static void updateDancers(List<Dancer> dancers) {
		
		dancers.stream().forEach(Dancer::updateNeighbours);
		
	}

	private static void eliminateDancers(List<Dancer> dancers) {
		long count = dancers.stream().filter(Dancer::isNotElminitated).count();
		dancers.stream().forEach(Dancer::eliminate);
		if(count != dancers.stream().filter(Dancer::isNotElminitated).count()) {
			hasEliminationOccured = true;
		}
		else {
			hasEliminationOccured = false;
		}
	}

	private static int turnScore(List<Dancer> dancers) {
		
		return dancers.stream().filter(Dancer::isNotElminitated).map(x->x.skill).reduce(0, Integer::sum);
	}

	private static boolean danceContinue(List<Dancer> dancers) {
		return dancers.stream().filter(Dancer::hasNeighbourds).count()>0 ? true : false;
	}








}



class Dancer{
	int skill;
	int c;
	int r;
	Dancer northDancer =null;
	boolean northUpdated = false;
	Dancer eastDancer=null;
	boolean easthUpdated = false;
	Dancer southDancer=null;
	boolean southUpdated = false;
	Dancer westDancer=null;
	boolean westUpdated = false;
	boolean isElminitated =false;
	
	public boolean isNotElminitated() {
		return !this.isElminitated;
	}
	
	public void updateNeighbours() {
		//TODO : if too slow update neighborhoods in both sides
		
		if(northDancer==null) {
			northDancer = null;
		}
		else {
			northDancer = getNextNorthNeighbourd();
			this.northUpdated = true;
			northDancer.southDancer = this;
			northDancer.southUpdated = true;
		}
		
		if(northDancer==null) {
			northDancer = null;
		}
		else {
			southDancer = getNextNorthNeighbourd();
			this.southUpdated = true;
			southDancer.northDancer = this;
			southDancer.northUpdated = true;
		}
		
		if(eastDancer==null) {
			eastDancer = null;
		}
		else {
			eastDancer = getNextEasthNeighbourd();
			this.easthUpdated = true;
			eastDancer.westDancer = this;
			eastDancer.westUpdated = true;
		}
		
		if(westDancer==null) {
			westDancer = null;
		}
		else {
			westDancer = getNextEasthNeighbourd();
			this.westUpdated = true;
			westDancer.eastDancer = this;
			westDancer.easthUpdated = true;
		}
		
		
//		eastDancer = eastDancer==null ? null : getNextEasthNeighbourd();
//		southDancer = southDancer==null ? null : getNextSouthNeighbourd();
//		westDancer = westDancer==null ? null : getNextWestNeighbourd();
	}
	
	private Dancer getNextNorthNeighbourd() {
		
		if(this.northDancer==null) return null;
		if(this.northDancer.isElminitated) {
			return this.northDancer.getNextNorthNeighbourd();
		}
		else {
			return this.northDancer;
		}
	}

	private Dancer getNextEasthNeighbourd() {
		
		if(this.eastDancer==null) return null;
		if(this.eastDancer.isElminitated) {
			return	this.eastDancer.getNextEasthNeighbourd();
		}
		else {
			return this.eastDancer;
		}
	}
	
private Dancer getNextSouthNeighbourd() {
		
		if(this.southDancer==null) return null;
		if(this.southDancer.isElminitated) {
			return this.southDancer.getNextSouthNeighbourd();
		}
		else {
			return this.southDancer;
		}
	}
	
private Dancer getNextWestNeighbourd() {
	
	if(this.westDancer==null) return null;
	if(this.westDancer.isElminitated) {
		return this.westDancer.getNextWestNeighbourd();
	}
	else {
		return this.westDancer;
	}
}
	
	public void eliminate() {
		float average =0;
		int count = 0;
		if(northDancer!=null) {
			count++;
			average += northDancer.skill;
		}
		if(eastDancer!=null){
			count++;
			average += eastDancer.skill;
		}
		if(southDancer!=null){
			count++;
			average += southDancer.skill;
		}
		if(westDancer!=null){
			count++;
			average += westDancer.skill;
		}
		average/=count;
		if(this.skill < average) {
			isElminitated = true;
		}
	}
	
	public boolean hasNeighbourds() {
		return northDancer == null && eastDancer == null && southDancer == null && westDancer==null ? false : true;
	}
	
	public int getNeighbourdsCount() {
		int count = 0;
		if(northDancer!=null)count++;
		if(eastDancer!=null)count++;
		if(southDancer!=null)count++;
		if(westDancer!=null)count++;
return count;
	}
	
	public Dancer(int r, int c ,int skill) {
		this.c = c;
		this.r = r;
		this.skill = skill;
	}
}