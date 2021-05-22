/**
 * Import data from a json file into the db
 */
const config = require('config')
const logger = require('../helpers/logger')
const helper = require('../helpers')

const filePath = helper.getParamFromCliArgs() || config.DEFAULT_DATA_FILE_PATH
const userPrompt = `WARNING: this would remove existing data. Are you sure you want to import data from a json file with the path ${filePath}?`
const dataModels = ['Criteria', 'DiscussionPost', 'Document', 'Evaluation', 'EvaluationResponse', 'Opportunity', 'OpportunityDocument',
  'OpportunityLink', 'OpportunityMembers', 'OpportunityPhase', 'OpportunityTag', 'OpportunityViews', 'Source', 'Tag', 'TechTheme', 'User']

async function importData () {
  await helper.promptUser(userPrompt, async () => {
    try {
      await helper.importData(filePath, dataModels)
      process.exit(0)
    } catch (err) {
      logger.logFullError(err, { component: 'importData' })
      process.exit(1)
    }
  })
}

importData()
