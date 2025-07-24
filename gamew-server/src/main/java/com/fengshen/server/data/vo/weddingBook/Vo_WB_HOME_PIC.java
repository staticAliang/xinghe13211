package com.fengshen.server.data.vo.weddingBook;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vo_WB_HOME_PIC {

	private String bookId;
	
	private Integer flag;

	public Vo_WB_HOME_PIC(String bookId, Integer flag) {
		super();
		this.bookId = bookId;
		this.flag = flag;
	}

	public Vo_WB_HOME_PIC() {
		super();
	}
	
}
