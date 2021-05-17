var DataTypes = require("sequelize").DataTypes;
var _criteria = require("./criteria");
var _document = require("./document");
var _evaluation = require("./evaluation");
var _evaluation_response = require("./evaluation_response");
var _opportunity = require("./opportunity");
var _opportunity_document = require("./opportunity_document");
var _opportunity_link = require("./opportunity_link");
var _opportunity_phase = require("./opportunity_phase");
var _opportunity_tag = require("./opportunity_tag");
var _opportunity_views = require("./opportunity_views");
var _source = require("./source");
var _tag = require("./tag");
var _tech_theme = require("./tech_theme");
var _user = require("./user");

function initModels(sequelize) {
  var criteria = _criteria(sequelize, DataTypes);
  var document = _document(sequelize, DataTypes);
  var evaluation = _evaluation(sequelize, DataTypes);
  var evaluation_response = _evaluation_response(sequelize, DataTypes);
  var opportunity = _opportunity(sequelize, DataTypes);
  var opportunity_document = _opportunity_document(sequelize, DataTypes);
  var opportunity_link = _opportunity_link(sequelize, DataTypes);
  var opportunity_phase = _opportunity_phase(sequelize, DataTypes);
  var opportunity_tag = _opportunity_tag(sequelize, DataTypes);
  var opportunity_views = _opportunity_views(sequelize, DataTypes);
  var source = _source(sequelize, DataTypes);
  var tag = _tag(sequelize, DataTypes);
  var tech_theme = _tech_theme(sequelize, DataTypes);
  var user = _user(sequelize, DataTypes);


  return {
    criteria,
    document,
    evaluation,
    evaluation_response,
    opportunity,
    opportunity_document,
    opportunity_link,
    opportunity_phase,
    opportunity_tag,
    opportunity_views,
    source,
    tag,
    tech_theme,
    user,
  };
}
module.exports = initModels;
module.exports.initModels = initModels;
module.exports.default = initModels;
