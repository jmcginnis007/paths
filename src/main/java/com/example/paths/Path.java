package com.example.paths;

import java.util.ArrayList;
import java.util.List;

public class Path {

	final static int PATH_SIZE_MAX = 3;
	List<Page> pages = new ArrayList<Page>();	
	
	public Path(List<Page> pages) {
		this.pages = pages;
	}
	
	public Path() {
		
	}

	public List<Page> getPages()
	{
		return pages;
	}
	
	public boolean isFull()
	{
		return pages.size() >= PATH_SIZE_MAX;
	}
	
	public Path addNextPage(Page nextPage)
	{
		pages.add(nextPage);
		return this;
	}
	
	public List<Page> getTrailingEntries()
	{
		List<Page> subarray = new ArrayList<Page>(pages.subList(PATH_SIZE_MAX - 2, PATH_SIZE_MAX));
		return subarray;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pages == null) ? 0 : pages.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Path other = (Path) obj;
		if (pages == null) {
			if (other.pages != null)
				return false;
		} else if (!pages.equals(other.pages))
			return false;
		return true;
	}
	
	@Override
	public String toString()
	{
		String result = "";
		for (Page page : pages)
		{
			result+=page.getName() + "-->";
		}
		return result;
	}
}
