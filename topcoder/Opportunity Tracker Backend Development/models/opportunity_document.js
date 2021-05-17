const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  return sequelize.define('opportunity_document', {
    opportunity_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    document_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
  }, {
    sequelize,
    tableName: 'opportunity_document',
    timestamps: false
  });
};
