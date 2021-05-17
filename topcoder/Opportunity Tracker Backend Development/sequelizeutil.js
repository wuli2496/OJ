const Sequelize = require('sequelize');
const config = require('./dbconfig');
const  initModel = require('./models/init-models');

var sequelize = new Sequelize(config.database, config.username, config.password, {
    host: config.host,
    dialect : 'mysql',
    pool: {
        max: 5,
        min: 0,
        idle: 30000
    }
});

const models = initModel(sequelize);

function initAssociation() {
    models.opportunity.hasOne(models.opportunity_phase, {foreignKey:'opportunity_id'});
    models.opportunity_phase.belongsTo(models.opportunity, {foreignKey:'opportunity_id'});

    models.opportunity.hasOne(models.opportunity_tag, {foreignKey:'opportunity_id'});
    models.opportunity_tag.belongsTo(models.opportunity, {foreignKey:'opportunity_id'});

    models.opportunity.hasOne(models.opportunity_views, {foreignKey:'opportunity_id'});
    models.opportunity_views.belongsTo(models.opportunity, {foreignKey:'opportunity_id'});

    models.user.hasOne(models.opportunity_views, {foreignKey:'user_id'});
    models.opportunity_views.belongsTo(models.user, {foreignKey:'user_id'});

    models.opportunity.hasOne(models.opportunity_tag, {foreignKey:'opportunity_id'});
    models.opportunity_tag.belongsTo(models.opportunity, {foreignKey:'opportunity_id'});
}

module.exports = {
    models:models,
    initAssociation:initAssociation
}

