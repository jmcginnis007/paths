package com.example.paths;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.Iterables;

public class App 
{
	final static Integer N = 10;  //value for N in "find the top N most frequently visited paths"
    public static void main( String[] args )
    {
    	Map<User, ArrayList<Path>> userPaths = new HashMap<>();
    	Map<Path, Integer> popularPaths = new HashMap<>();
    	
    	List<DatafeedItem> data =  new ArrayList<>();
    	data.add(new DatafeedItem(new User("U1"), new Page("/")));
    	data.add(new DatafeedItem(new User("U1"), new Page("subscribers")));
    	data.add(new DatafeedItem(new User("U2"), new Page("/")));
    	data.add(new DatafeedItem(new User("U2"), new Page("subscribers")));
    	data.add(new DatafeedItem(new User("U1"), new Page("filter")));
    	data.add(new DatafeedItem(new User("U1"), new Page("export")));
    	data.add(new DatafeedItem(new User("U2"), new Page("filter")));
    	data.add(new DatafeedItem(new User("U2"), new Page("export")));
    	data.add(new DatafeedItem(new User("U3"), new Page("/")));
    	data.add(new DatafeedItem(new User("U3"), new Page("catalog")));
    	data.add(new DatafeedItem(new User("U3"), new Page("edit")));
		
		Stream<DatafeedItem> sequentialStream = data.stream();
	    sequentialStream.forEach(dataStreamItem -> 
	    {
	    	if (userPaths.get(dataStreamItem.getUser()) != null)
	    	{
	    		// user is already in the map
	    		// get latest path to see if we can add to it
	    		ArrayList<Path> allPaths = userPaths.get(dataStreamItem.getUser());
	    		Path latestPath = Iterables.getLast(allPaths);	    		
	    		// see if latest path is full
	    		if (latestPath.isFull())
	    		{
	    			// we can't add to it, so create a new path with the new page and the trailing entries from the last page
	    			allPaths.add(new Path(latestPath.getTrailingEntries())
	    					.addNextPage(dataStreamItem.getPage()));
	    		}
	    		else
	    		{
	    			// not full so add to it
	    			latestPath.addNextPage(dataStreamItem.getPage());
	    		}
	    		userPaths.put(dataStreamItem.getUser(), allPaths);
	    	}
	    	else
	    	{
	    		// new user entry
	    		ArrayList<Path> allPaths = new ArrayList<>();
	    		allPaths.add(new Path().addNextPage(dataStreamItem.getPage()));
	    		userPaths.put(dataStreamItem.getUser(), allPaths);
	    	}
	    	    	
	    });
	    
	    // we now have a map with each user's list of paths
	    // now find the most popular paths
	    userPaths.forEach((user,paths) -> 
	    {
	    	paths.forEach(path ->
	    	{
	    		if (!popularPaths.containsKey(path))
		    	{
		    		popularPaths.put(path, 1);
		    	}
		    	else
		    	{
		    		popularPaths.put(path, popularPaths.get(path) + 1);
		    	}
	    	});
	    	
	    });
	    
	    popularPaths.entrySet().stream()
	    .sorted(Map.Entry.<Path, Integer>comparingByValue().reversed())
	    .limit(N)
	    .forEach(System.out::println);
    }
}