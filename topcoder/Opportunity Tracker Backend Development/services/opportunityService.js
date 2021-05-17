const {models, initAssociation} = require('../sequelizeutil');
const Sequelize = require('sequelize');
const Op = Sequelize.Op;

initAssociation();

async function query(req, res, next) {
    let queryOptions = {};
    let page = 1;
    let pageSize = 10;
    if (req.query.page) {
        page = Number.parseInt(req.query.page);
    }

    if (req.query.pageSize) {
        pageSize = Number.parseInt(req.query.pageSize);
    }

    queryOptions.limit = pageSize;
    queryOptions.offset = (page - 1) * pageSize;

    if (req.query.status) {
        queryOptions.where = {'$opportunity_phase.status$':{[Op.in]:[req.query.status]}};
        queryOptions.include = [{model: models.opportunity_phase, as:'opportunity_phase'}];
    }

    if (req.query.productName) {
        queryOptions.where.name = {[Op.eq]:req.query.productName};
    }

    if (req.query.companyName) {
        queryOptions.where.company = {[Op.eq]: req.query.companyName};
    }

    if (req.query.technologyTheme) {
        queryOptions.where.tech_theme_id = {[Op.in]:req.query.technologyTheme};
    }
    
    if (req.query.lastUpdatedStart && !req.query.lastUpdatedEnd) {
        queryOptions.where.updated_on = {[Op.gte]:req.query.lastUpdatedStart};
    } else if (!req.query.lastUpdatedStart && req.query.lastUpdatedEnd) {
        queryOptions.where.updated_on = {[Op.lte]: req.query.lastUpdatedEnd};
    } else if (req.query.lastUpdatedStart && req.query.lastUpdatedEnd) {
        queryOptions.where.updated_on = {[Op.gte]:req.query.lastUpdatedStart,
        [Op.lte]:req.query.lastUpdatedEnd};
    }

    if (req.query.owner) {
        queryOptions.where['$opportunity_view->user.name$'] = {[Op.eq]: req.query.owner};
        queryOptions.include.push({model:models.opportunity_views, as:'opportunity_view', include:{model:models.user, as:'user'}});
    } 
    
    if (req.query.tags) {
        queryOptions.where['$opportunity_tag.tag_id$'] = {[Op.in]:[req.query.tags]};
        queryOptions.include.push({model:models.opportunity_tag, as:'opportunity_tag'});
    }

    let result = await models.opportunity.findAll(queryOptions);

    res.status(200).json(result);
}

module.exports={
    query
}
