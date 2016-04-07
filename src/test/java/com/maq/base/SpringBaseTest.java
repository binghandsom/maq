package com.maq.base;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@TransactionConfiguration(defaultRollback=false)
@ContextConfiguration(locations = {"/spring_core/applicationContext.xml","/spring_core/service.xml"})
public class SpringBaseTest extends AbstractTransactionalJUnit4SpringContextTests{

}
