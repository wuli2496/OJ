import Box from '@mui/material/Box';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import * as React from 'react';
import PropTypes from 'prop-types';
import Typography from '@mui/material/Typography';

import DataTable from './DataTable';
import BasicTimeline from "./BasicTimeline";
function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`simple-tabpanel-${index}`}
            aria-labelledby={`simple-tab-${index}`}
            {...other}
        >
            {value === index && (
                <Box sx={{ p: 3 }}>
                    <Typography>{children}</Typography>
                </Box>
            )}
        </div>
    );
}
TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.number.isRequired,
    value: PropTypes.number.isRequired,
};

export default function BasicTabs() {
    const [value, setValue] = React.useState(0);
    const [data, setData] = React.useState([]);
    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    const handleData = (newData) => {
        console.log(newData);
        setData(newData);
    }
    let isDisabled = {}
    if (data.length === 0) {
        isDisabled = {disabled:true};
    }
    console.log(isDisabled);

    return (
        <Box sx={{ width: '100%' }} className="tabPanel">
            <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
                <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
                    <Tab label="Item One" id="tabTable"/>
                    <Tab label="Item Two" id="tabCard"  {...isDisabled}/>
                </Tabs>
            </Box>

            <TabPanel value={value} index={0} >
                <DataTable handle={handleData}></DataTable>
            </TabPanel>
            <TabPanel value={value} index={1} >
                <BasicTimeline source={data}></BasicTimeline>
            </TabPanel>
        </Box>
    );
}