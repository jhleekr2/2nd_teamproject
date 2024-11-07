package view.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import view.dao.face.SnsDao;
import view.dto.Content;
import view.service.face.SnsService;

@Service
@Slf4j
public class SnsServiceImpl implements SnsService {

	@Autowired
	private SnsDao snsDao;
	
	@Override
	public List<Content> list() {

		log.info("SnsService.list() 호출");
		return snsDao.View();
	}



}
