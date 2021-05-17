const Sequelize = require('sequelize');
module.exports = function(sequelize, DataTypes) {
  const opportunity_tag = sequelize.define('opportunity_tag', {
    opportunity_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    },
    tag_id: {
      type: DataTypes.INTEGER,
      allowNull: true
    }
  }, {
    sequelize,
    tableName: 'opportunity_tag',
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

  opportunity_tag.removeAttribute('id');
  return opportunity_tag;
};
