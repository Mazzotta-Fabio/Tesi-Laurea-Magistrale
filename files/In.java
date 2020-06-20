package dataset;

import java.io.*;
import java.util.*;

//classe che si occupa di gestire l'input
public class InputDataset {
	private ArrayList<String> buf;
	private String [][]picture;
	private int row;
	private int col;
	
	public InputDataset() {
		buf=new ArrayList<String>();
	}
	
	//caricamento picture e buffer
	public void loadDataset(FileReader inMatrix) throws IOException{
		Scanner lettore=new Scanner(inMatrix);
		row=Integer.parseInt(lettore.nextLine());
		col=Integer.parseInt(lettore.nextLine());
		picture=new String[row][col];
		int i=0,in=0;
		while(lettore.hasNextLine()) {
			String [] buf=lettore.nextLine().split(" ");
			for(int j=0;j<buf.length;j++) {
				this.buf.add(buf[j]);
				picture[i][j]=""+in;
				in++;
			}
			i++;
		}
		lettore.close();
	}
	
	//ottieni token
	public String getToken(int i) {
		return buf.get(i);
	}
    
	//metodo per settare un valore trovato 
	public void setTokenFound(int index,ArrayList<String> tokenViews) {
		if(tokenViews.size()==0) {
			tokenViews.add(""+index);
		}
		else {
			boolean flag=true;
			for(String t:tokenViews) {
				if(t.equals(""+index)) {
					flag=false;
				}
			}
			if(flag) {
				tokenViews.add(""+index);
			}
		}
	}
	
	//verifica se e' stato visto il token index
	private boolean isViewed(String index,ArrayList<String> tokenViews) {
		for(String s:tokenViews) {
			if(s.equals(index)){
				return false;
			}
		}
		return true;
	}
	
	//metodo che calcola il simbolo da processare
	public int getNextToken(int index,String dirSymbol,ArrayList<String> tokenViews){
		int i,j,newCol=0,newRow=0;
		for(i=0;i<row;i++){
			for(j=0;j<col;j++){
				if((picture[i][j]!=null)&&(picture[i][j].equals(""+index))){
					switch(dirSymbol){
					case "<":
						if(i==(row-1)) {newRow=0;}else {newRow=i+1;}
						newCol=0;
						break;
					case ">":
						if(j==(col-1)) {newCol=0;}else {newCol=j+1;}
						newRow=0;
						if(picture[i][newCol]==null) {newCol=0;}
						break;
					}
					for(int k=newRow;k<row;k++) {
						for(int l=newCol;l<col;l++) {
							if((picture[k][l]!=null)&&(isViewed(picture[k][l],tokenViews))) {
								return Integer.parseInt(picture[k][l]);
							}
						}
					}
				}
			}
		}
		return -1;
	}
}