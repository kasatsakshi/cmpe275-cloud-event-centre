import React, { useEffect } from 'react'
import EventNavbar from './EventNavbar';
import './SystemReport.css';
import axios from "axios";
import moment from "moment";
import { PieChart, Pie, Sector, Cell, Tooltip, ResponsiveContainer } from 'recharts';
import { useDispatch, useSelector } from "react-redux";

function UserReport() {
  const [paidData, setPaidData] = React.useState([]);
  const [systemData, setSystemData] = React.useState([]);
  const [mimicDate, setMimicDate] = React.useState(null);
  const COLORS = ['#e75d45', '#AEB2B0'];
  const { currentUser } = useSelector((state) => state.user);
  useEffect(() => {
    console.log(currentUser.id);
    axios
      .get("/api/time")
      .then((res) => {
        console.log(res.data);
        const dateObj = new Date();
        setMimicDate(moment(res.data).format("YYYY-MM-DD HH:mm"));
        const date = moment(mimicDate).format("YYYY-MM-DD");
        const hourMinute = moment(mimicDate).format("HH:mm");
        const time = date + "T" + hourMinute;

        var userid = currentUser.id;
        axios
          .get(`/api/report/user/${userid}`, {
            params: {
              time: time
            },
          })
          .then((res) => {
            console.log(res.data);
            const paidData = [
              { name: 'unpaid Events', value: 100 - res.data.percentPaidEvents },
              { name: 'Paid Events', value: res.data.percentPaidEvents }
            ];
            setPaidData(paidData);
            setSystemData(res.data);
          }).catch((err) => {
            console.log(err);
          });
      })
      .catch((err) => {
        console.log(err);
      });
  }, [mimicDate]);

  return (
    <div>
      <EventNavbar />
      <div className='report-container'>
        <h1>User Reports</h1> <br /><br />
        <h4>Participant Report</h4>
        <div className='events-created'>
          <h6>Number of signed up events: {systemData.signups}</h6>
          <h6>Number of rejects: {systemData.rejects}</h6>
          <h6>Number of approvals: {systemData.approvals}</h6>
          <h6>Number of finished events: {systemData.participantFinishedEvents}</h6>


          {/* <h6>Percent of paid events: {systemData.percentPaidEvents}%</h6>
          <ResponsiveContainer width="100%" height={280}>
            <PieChart width={400} height={400}>
              <Pie
                dataKey="value"
                isAnimationActive={false}
                data={paidData}
                cx="50%"
                cy="50%"
                outerRadius={80}
                fill="#e75d45"
                label="value">
                {paidData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                ))}
              </Pie>
              <Tooltip />
            </PieChart>
          </ResponsiveContainer> */}
        </div>
        {/* <div>
          <h6>Number of Cancelled events: {systemData.canceledEvent}</h6>
          <h6>Total number of participation request / minimum participant required: {systemData.requestsPerMinParticipants}</h6>
          <h6 className='report-heading'>Number of Finished Events: {systemData.finishedEvents}</h6>
          <h6>Average participants: {systemData.avgParticipants}</h6>
        </div> */}
        <br /> <br />
        <h4>Organizer Report</h4>
        <div className='events-created'>
          <h6>Number of created events: {systemData.createdEvents}</h6>
          <h6>Percent of paid events: {systemData.percentPaidEvents}</h6>
          <h6>Number of canceled events: {systemData.canceledEvents}</h6>
          {/* <h6>Number of participant requests: {systemData.participantFinishedEvents}</h6> */}
          <h6>Number of finished events: {systemData.organiserFinishedEvents}</h6>
          <h6>Average Number of participants for finished events: {systemData.avgParticipants}</h6>

          <h6>Number of finished paid events: {systemData.finishedPaidEvents}</h6>
          <h6>Total Revenue from finished paid events: {systemData.totalRevenue}</h6>



          {/* <h6>Percent of paid events: {systemData.percentPaidEvents}%</h6>
          <ResponsiveContainer width="100%" height={280}>
            <PieChart width={400} height={400}>
              <Pie
                dataKey="value"
                isAnimationActive={false}
                data={paidData}
                cx="50%"
                cy="50%"
                outerRadius={80}
                fill="#e75d45"
                label="value">
                {paidData.map((entry, index) => (
                  <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                ))}
              </Pie>
              <Tooltip />
            </PieChart>
          </ResponsiveContainer> */}
        </div>
      </div>
    </div>
  )
}

export default UserReport;