public class ParserSplitTest {
	public static void main(String[] args){
		//add normal task
		Command add = ParserSplit.parse("add taskname; 1000-1100 on 01/01/2015; 1");
		System.out.println(add.getCommandType()+ " "+add.getTaskType());
		System.out.println(add.getTaskName()+" " + add.getstartTime() +" " +add.getEndTime()+" "+add.getDate()+" "+add.getPriority());
		
		//add deadline
		Command addDead = ParserSplit.parse("add taskname; by 1000 on 01/01/2015");
		System.out.println(addDead.getCommandType()+ " "+addDead.getTaskType());
		System.out.println(addDead.getTaskName() +" "+ addDead.getEndTime()+" "+addDead.getDate());
		
		//add recurring task
		Command addRec = ParserSplit.parse("add taskname; 1000-1100 every Tuesday; 1");
		System.out.println(addRec.getCommandType() + " "+addRec.getTaskType());
		System.out.println(addRec.getTaskName()+" "+addRec.getstartTime()+ " "+addRec.getEndTime()+" "+addRec.getDay()+" "+addRec.getPriority());
		
		//add floating task
		Command addFlo = ParserSplit.parse("add taskname");
		System.out.println(addFlo.getCommandType()+ " "+addFlo.getTaskType());
		System.out.println(addFlo.getTaskName());
		
		
		//delete floating task
		Command deleteFlo = ParserSplit.parse("delete floating 3");
		System.out.println(deleteFlo.getCommandType()+ " "+deleteFlo.getTaskType());
		System.out.println(deleteFlo.getNumber());
		
		//delete normal task
		Command delete = ParserSplit.parse("delete 01/01/2015 3");
		System.out.println(delete.getCommandType()+ " "+delete.getTaskType());
		System.out.println(delete.getDate() +" "+delete.getNumber());
		
		//delete recurring task
		Command deleteRec = ParserSplit.parse("delete rec 4");
		System.out.println(deleteRec.getCommandType()+ " "+deleteRec.getTaskType());
		System.out.println(deleteRec.getNumber());
		
		//search keyword
		Command search = ParserSplit.parse("search apple");
		System.out.println(search.getCommandType());
		System.out.println(search.getKeyWord());
		
		
		
	}
}
