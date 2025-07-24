package com.fengshen.web.controller.sys.chara;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fengshen.core.util.ResponseView;
import com.fengshen.web.controller.BaseController;
import com.github.pagehelper.Page;

@RequestMapping("/charaPet")
@RestController
public class CharaPetController extends BaseController {

	@PostMapping("/getCharaPets")
	public ResponseView getCharaPets(Page<Object> pet) {
		
		return null;
	}
	
}