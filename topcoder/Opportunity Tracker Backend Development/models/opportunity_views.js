const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  const opportunity_views = sequelize.define('opportunity_views', {
    opportunity_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    user_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
  }, {
    sequelize,
    tableName: 'opportunity_views',
    timestamps: false,
    indexes: [
      {
        name: "opportunity_id",
        using: "BTREE",
        fields: [
          { name: "opportunity_id" },
        ]
      },
    ]
  });
  opportunity_views.removeAttribute('id');
  return opportunity_views;
};
