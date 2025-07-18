import * as React from 'react';
import Timeline from '@mui/lab/Timeline';
import TimelineItem from '@mui/lab/TimelineItem';
import TimelineSeparator from '@mui/lab/TimelineSeparator';
import TimelineConnector from '@mui/lab/TimelineConnector';
import TimelineContent from '@mui/lab/TimelineContent';
import TimelineDot from '@mui/lab/TimelineDot';


export default function BasicTimeline(props) {
    const datas = props.source;
    const tmp = datas.map((data) => {
        return (
        <TimelineItem>
            <TimelineSeparator>
                <TimelineDot />
                <TimelineConnector />
            </TimelineSeparator>
            <TimelineContent>{data.name}</TimelineContent>
        </TimelineItem>
        )
    });
    return (
        <Timeline>
            {tmp}
        </Timeline>
    );
}
