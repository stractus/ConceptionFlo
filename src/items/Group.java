package items;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import main.Main;
import xmlParser.ParserGroup;

public class Group {
	public LinkedList<Item_In_Group> item_list;
	public String name;
	public String entireName;
	public static List<Integer> groupCount;
	
	public Group(String name,String entireName,LinkedList<Item_In_Group> itemList){
		this.name=name;
		this.entireName=entireName;
		item_list = new LinkedList<Item_In_Group>();
		for(int i=0;i<itemList.size();i++){
			Item_In_Group item = itemList.get(i).clone();
			if(item!=null){
				item_list.add(itemList.get(i).clone());
			}
		}
		
		if(itemList.size() != item_list.size()){
			System.out.println("probl�me lors du chargement d'un item dans le groupe : "+name);
		}
	}
	
	private Group(String path){
		Group g = ParserGroup.parse(path);
		if(g==null){
			System.out.println("Probl�me avec le groupe : "+path);
		}
		else{
			copy(g);
		}
	}
	
	private void copy(Group g) {
		this.name=g.name;
		this.entireName=g.entireName;
		item_list = new LinkedList<Item_In_Group>();
		for(int i=0;i<g.item_list.size();i++){
			item_list.add(g.item_list.get(i));
		}
	}

	public static List<Group> get_all_groups(){
		LinkedList<Group> group_list = new LinkedList<Group>();
		
		File repertoire = new File(System.getProperty("user.dir") + Item.systemSeparator + "groups");
		File[] files=repertoire.listFiles();
		groupCount = new LinkedList<Integer>();
		for(int i=0;i<files.length;i++){
			if(files[i].isFile() && files[i].getName().endsWith(".xml")){
				group_list.add(new Group(files[i].getAbsolutePath()));
				groupCount.add(0);
			}
		}
		
		return group_list;
	}
	
	public static void putGroup(int x,int y,int groupId){
		if(Main.group_list.size()<=groupId){
			System.out.println("Problem in 'putGroup' : groupId >= group_list.size()");
			return ;
		}
		Group g = Main.group_list.get(groupId);
		
		for(int i=0;i<g.item_list.size();i++){
			if(Main.grille[x+(i*2)][y]!=null){
				return ;
			}
		}
		groupCount.set(groupId, groupCount.get(groupId)+1);
		for(int i=0;i<g.item_list.size();i++){	
			Main.grille[x+(i*2)][y]=new ItemOnMap(Main.item_list.get(g.item_list.get(i).id),g.item_list.get(i).name,g.name,groupCount.get(groupId));
		}
		
		
	}
	
}
