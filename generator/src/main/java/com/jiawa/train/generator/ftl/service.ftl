package com.jiawa.train.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.PageInfo;
import com.jiawa.train.common.context.LoginMemberContext;
import com.jiawa.train.common.response.PageResp;
import com.jiawa.train.common.util.SnowUtil;
import com.jiawa.train.member.domain.${Domain};
import com.jiawa.train.member.domain.${Domain}Example;
import com.jiawa.train.member.mapper.${Domain}Mapper;
import com.jiawa.train.member.req.${Domain}QueryReq;
import com.jiawa.train.member.req.${Domain}SaveReq;
import com.jiawa.train.member.response.${Domain}QueryResp;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ${Domain}Service {
    @Resource
    private ${Domain}Mapper mapper;

    public void save(${Domain}SaveReq req) {
        DateTime now = DateTime.now();
        ${Domain} ${domain} = BeanUtil.copyProperties(req, ${Domain}.class);
        if (ObjectUtil.isNull(req.getId())) {
            ${domain}.setId(SnowUtil.getSnowFlakeNextId());
            ${domain}.setMemberId(LoginMemberContext.getMember().getId());
            ${domain}.setCreateTime(now);
            ${domain}.setUpdateTime(now);
            mapper.insert(${domain});
        } else {
            ${domain}.setUpdateTime(now);
            mapper.updateByPrimaryKey(${domain});
        }
    }

    public PageResp<${Domain}QueryResp> queryList(${Domain}QueryReq req) {
        ${Domain}Example ${domain}Example = new ${Domain}Example();
        ${domain}Example.setOrderByClause("id desc");
        ${Domain}Example.Criteria criteria = ${domain}Example.createCriteria();
        if (ObjectUtil.isNotNull(req.getMemberId()))
            criteria.andMemberIdEqualTo(req.getMemberId());
//        PageHelper.startPage(req.getPage(),req.getSize());
        List<${Domain}> ${domain}s = mapper.selectByExample(${domain}Example);
        PageInfo<${Domain}> pageInfo = new PageInfo<>(${domain}s);
        List<${Domain}QueryResp> ${domain}QueryResps = BeanUtil.copyToList(${domain}s, ${Domain}QueryResp.class);
        PageResp<${Domain}QueryResp> pageResp = new PageResp<>();
        pageResp.setList(${domain}QueryResps);
        pageResp.setTotal(pageInfo.getTotal());
        return pageResp;
    }

    public void delete(Long id) {
        mapper.deleteByPrimaryKey(id);
    }
}
